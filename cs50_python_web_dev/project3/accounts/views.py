from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django.contrib.auth import login, authenticate
from django.urls import reverse_lazy
from django.views import generic
from django.shortcuts import redirect
from django import forms

# add email, first name and last name to the signup form
class UserCreateForm(UserCreationForm):
    email = forms.EmailField(max_length=64, required=True)
    first_name = forms.CharField(max_length=28, required=True)
    last_name = forms.CharField(max_length=28, required=True)

    class Meta:
        model = User
        fields = ("username", "email", "first_name", "last_name", "password1", "password2")

# create a custom view for the signup auth route
class SignUp(generic.CreateView):
    form_class = UserCreateForm
    success_url = reverse_lazy('index')
    template_name = 'signup.html'

    # redirect user to the home page if the user is already authenticated but trying to access signup
    def get(self, request):
        if self.request.user.is_authenticated:
            return redirect('index')
        else:
            # otherwise do normal signup view GET behavior
            return super(SignUp, self).get(request)

    # authenticate and redirect the user after signup
    def form_valid(self, form):
        afterValidForm = super().form_valid(form)

        # create authenticated user
        user = authenticate(
            username=form.cleaned_data["username"],
            password=form.cleaned_data["password1"]
        )

        # log the user in
        login(self.request, user)

        # return the original behavior
        return afterValidForm