# Project 0
Web Programming with Python and JavaScript
Brett Bloethner | Fall 2019

## Description
For my project 0 I made a simple series of HTML pages that display the original articles of the US Constitution. I went with this idea because it let me mess around with a relativly large ammount of content (about 4000k words) and provided data that could easily be represented in nicely formatted web pages with tables and simple navigation. This project is also published to github pages and can be accessed at the url [https://bl8nr.github.io/project0-bl8nr/](https://bl8nr.github.io/project0-bl8nr/). I also used an html beautifier for this project so I'm hoping that made the HTML syntax a bit easier to read.

IMPORTANT NOTE: I included an assignment checklist primarily for my own task tracking. However, I added some notes to each task that you might find useful when looking for specific elements of the website that meet individual project requirements. This checklist can be found by simply scrolling down to the bottom of this markdown page!

## File Descriptions
##### index.html
This is the home page for the project. It includes text of the Preamble of the US Constitution as well as a small menu to link to the 7 Articles pages, an image of the Constitution and a navigation bar.
##### article_1.html, article_2.html, article_3.html, article_4.html, article_5.html, article_6.html, article_7.html
Each of these html pages contains the text for one of the seven of the first articles of the US Constitution. In each of these files you'll find paragraph text formatting as well as a navigation bar, header and links to sections on that same page. Some of these pages may also contain tables or lists.
##### sources.html
This file uses a table to outline what sources I used while making this project. It also includes a navigation bar.
##### /css/styles.scss
This file includes all of the scss styling I did for this project. Its transpiled and read by the browser as the /css/styles.css file.
##### /images/usc.jpg
This file is the image of the US Constitution that you'll find on the index.html page.

## Assignment Checklist
#### General Requirements
- [x] the website must contain at least four different .html pages
    - [x] the website contains 9 .html pages
- [x] it should be possible to get from any page on the site to any other page by following one or more hyperlinks
    - [x] all pages are accessible via the navigation bar which exists at the top of each page
- [x] the website must include at least one list (ordered or unordered)
    - [x] list used on each .html page in the navigation bar
    - [x] list used on each article_*.hmtl page for the section quick links
    - [x] list used for text formatting in files article_1.html and article_7.html
    - [x] list used for the Articles link menu in index.html
- [x] the website must include at least one table
    - [x] table is used in file article_7.html
    - [x] table is used in file sources.html
- [x] the website must include at least one image
    - [x] and image of the USC is included on the index.html page
- [x] the website must include at least one stylesheet file
    - [x] the website includes a stylesheet at ./css/styles.scss

#### CSS Requirements
- [x] the stylesheet must use at least five different CSS properties
    - [x] display used in ./css/styles.scss
    - [x] content used in ./css/styles.scss
    - [x] color used in ./css/styles.scss
    - [x] font-family used in ./css/styles.scss
    - [x] max-width used in ./css/styles.scss
    - [x] mac-height used in ./css/styles.scss
    - [x] numerous other CSS properties are also used in ./css/styles.scss
- [x] the stylesheet must use at least five different types of CSS selectors
    - [x] id selector is used in ./css/styles.scss
    - [x] class selector is used in ./css/styles.scss
    - [x] element selector is used in ./css/styles.scss
    - [x] element>element selector is used in ./css/styles.scss
    - [x] element+element selector is used in ./css/styles.scss
    - [x] ::before selector is used in ./css/styles.scss
    - [x] :hover selector is used in ./css/styles.scss
- [x] the stylesheet must use the #id selector at least once
    - [x] id selector is used in ./css/styles.scss for #article-list and #preamble
- [x] the stylesheet must use the .class selector at least once
    - [x] class selector is used numerous times in ./css/styles.scss
- [x] the stylesheet must use at least one mobile-response @media query
    - [x] a @media query shortens the navbar title for smaller screens
    - [x] a @media query hides content for when the page is being printed
- [x] the @media query should change something for smaller screens
    - [x] the @media query shortens the navbar title for smaller screens

#### Framework Requirements
- [x] the website must use Bootstrap 4
    - [x] Bootstrap 4 CSS loaded in the head of each html page via the Bootstrap CDN
    - [x] Bootstrap 4 JS loaded at the bottom of the body of each html page via the Bootstrap CDN
- [x] the website must incorporate at least one Bootstrap component
    - [x] Bootstrap component nav bar included in every html page
    - [x] Bootstrap component card included in every article_*.html page and index.html
- [x] the website must include Bootstraps grid model using at least two columns
    - [x] Bootstrap grid (container, row, columns) used through out the application
    - [x] 2 column responsive Bootstrap grid used on the index.html page

#### SCSS Requirements
- [x] the stylesheet must use at least one SCSS variable
    - [x] the stylesheet uses multiple scss variables for font color in ./css/styles.scss
- [x] the stylesheet must use at least one example of SCSS nesting
    - [x] css/styles.scss contains an example of nesting
- [x] the stylesheet must use at least one example of SCSS inheritance
    - [x] css/styles.scss contains and example of inheritance

#### README.md Requirements
- [x] a README must be included in markdown format
- [x] the README must include a description of the project
- [x] the README must describe whats in each file
- [x] the README must provide some additional information to the staff if necessary
- [x] include a assignemnt checklist in the README file