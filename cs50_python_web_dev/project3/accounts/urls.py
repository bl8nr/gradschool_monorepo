from django.urls import path
from django.contrib.auth import views as auth_views
from . import views

# routes here were extracted from django.contrib.auth views routes (to allow custom views)
# https://github.com/django/django/blob/master/django/contrib/auth/urls.py
# redirect_authenticated_user makes the login page redirect to the LOGIN_REDIRECT_URL in settings.py

urlpatterns = [

    # configure the signup route to use a custom view specd out in accounts/view.py
    path('signup/', views.SignUp.as_view(), name='signup'),

    # configure the login route to redirect if user is authenticated
    path('login/', auth_views.LoginView.as_view(redirect_authenticated_user=True), name='login'),

    # standard django auth logout route
    path('logout/', auth_views.LogoutView.as_view(), name='logout')
]
