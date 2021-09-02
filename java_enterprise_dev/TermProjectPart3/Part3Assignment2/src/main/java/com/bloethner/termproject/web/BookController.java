package com.bloethner.termproject.web;

import com.bloethner.termproject.entities.Book;
import com.bloethner.termproject.service.BookService;
import com.bloethner.termproject.util.BookGrid;
import com.bloethner.termproject.util.Message;
import com.bloethner.termproject.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/books")
@Controller
public class BookController {

    private BookService bookService;
    private MessageSource messageSource;

    /**
     * /books
     * render list view for viewing books
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {

        // fetch all books
        List<Book> books = bookService.findAll();

        // make all books available for template
        uiModel.addAttribute("books", books);

        // render book list view
        return "books/list";
    }

    /**
     * /books/${id}
     * render single book view via book id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {

        // make the book available for template
        uiModel.addAttribute("book", bookService.findById(id));

        // render book show view
        return "books/show";
    }

    /**
     * /books/${2}?form
     * render update book form via book id and form param
     * require authorization
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {

        // make the book available for template
        uiModel.addAttribute("book", bookService.findById(id));

        // render book update view
        return "books/update";
    }

    /**
     * /books/${id}?form
     * delete a book and redirect to the /books listing show route
     * require authorization
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.DELETE)
    public String deleteBook(@PathVariable("id") Long id, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {

        // delete book by id
        bookService.deleteById(id);

        // add flash message for successful book deletion
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("book_delete_success", new Object[]{}, locale)));

        // redirect to /books listing
        return "redirect:/books";
    }

    /**
     * /books/${id}?form
     * update a book and redirect to the books /books/${id} show route
     * require authorization
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Book book, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {

        // if the form has errors, reload page and render error messages on form
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("book", book);
            return "books/update";
        }
        uiModel.asMap().clear();

        // save book changes
        bookService.save(book);

        // add flash message for successful book save
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("book_save_success", new Object[]{}, locale)));

        // redirect to the view page for the mutated book
        return "redirect:/books/" + UrlUtil.encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
    }

    /**
     * /books?form
     * render the create book view/form
     * require authorization
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {

        // make new empty book available for template
        uiModel.addAttribute("book", new Book());

        // render the create template
        return "books/create";
    }

    /**
     * /books
     * create a new book from the data supplied then redirect to the /books list page
     * require authorization
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Book book, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {

        // if the form has errors, reload page and render error messages on form
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("book", book);
            return "books/create";
        }
        uiModel.asMap().clear();

        // save new book
        bookService.save(book);

        // add flash message for successful book save
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("book_save_success", new Object[]{}, locale)));

        // redirect to the view page for the created book
        return "redirect:/books/" + UrlUtil.encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
    }

    /**
     * /listgrid
     * Return books according to pagination and sort values
     * Used for jpgrid AJAX requests
     */
    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces="application/json")
    public BookGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "rows", required = false) Integer rows,
                               @RequestParam(value = "sidx", required = false) String sortBy,
                               @RequestParam(value = "sord", required = false) String order) {

        // determine if a sort if necessary and sort, if so
        Sort sort = null;
        String orderBy = sortBy;

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // after sort, pluck out the page requested and set in a Page<book> to be returned
        PageRequest pageRequest = null;

        if (sort != null) {
            pageRequest =  PageRequest.of(page - 1, rows, sort);
        } else {
            pageRequest = PageRequest.of(page - 1, rows);
        }

        Page<Book> bookPage = bookService.findAllByPage(pageRequest);

        // create new book grid for the front end supplying all data needed by the data grid
        BookGrid bookGrid = new BookGrid();
        bookGrid.setCurrentPage(bookPage.getNumber() + 1);
        bookGrid.setTotalPages(bookPage.getTotalPages());
        bookGrid.setTotalRecords(bookPage.getTotalElements());
        bookGrid.setBookData(Lists.newArrayList(bookPage.iterator()));

        return bookGrid;
    }

    /**
     * Autowire bookService and messageSource
     */
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
