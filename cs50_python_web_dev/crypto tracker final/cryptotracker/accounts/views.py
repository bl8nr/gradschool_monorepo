from django.contrib.auth.forms import UserCreationForm
from django.urls import reverse_lazy
from django.contrib.auth.models import User
from django.shortcuts import redirect
from django.views import generic
from django.contrib.auth import login, authenticate
from django import forms


# bring in the user creation form to add to a custom signup view
class UserCreateForm(UserCreationForm):
    email = forms.EmailField(max_length=64, required=True)

    class Meta:
        model = User
        fields = ("email", "password1", "password2")

# create the custom view for the signup auth route
class SignUp(generic.CreateView):
    form_class = UserCreationForm
    success_url = reverse_lazy('index')
    template_name = 'signup.html'

    # redirect user to index page if they're already authenticated
    def get(self, request):
        if self.request.user.is_authenticated:
            return redirect('index')
        else:
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
