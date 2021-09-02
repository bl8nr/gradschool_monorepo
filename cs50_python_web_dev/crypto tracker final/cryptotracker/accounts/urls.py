from django.urls import path
from django.contrib.auth import views as auth_views
from . import views

urlpatterns = [

    # create the path for signup and link it to a custom view
    path('signup/', views.SignUp.as_view(), name='signup'),

    # create the path for login and assign a page redirect to it
    path('login/', auth_views.LoginView.as_view(redirect_authenticated_user=True), name='login'),

    # create the path for logout, use default view
    path('logout/', auth_views.LogoutView.as_view(), name='logout')
]