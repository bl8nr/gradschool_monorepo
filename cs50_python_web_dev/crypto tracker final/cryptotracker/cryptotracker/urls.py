"""cryptotracker URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path
from django.views.generic import RedirectView

urlpatterns = [

    # redirect the root index route to the tracker url
    path('', RedirectView.as_view(url='/tracker')),

    # set the tracker/ url to use the tracker app and urls
    path('tracker/', include('tracker.urls')),

    # set the accounts/ url to user the account app and urls
    path('accounts/', include('accounts.urls')),

    # set the admin/ url to use the default admin ui urls
    path('admin/', admin.site.urls),
]
