from django.contrib import admin
from django.urls import include, path

urlpatterns = [

    # make the orders app handle root endpoints
    path("", include("orders.urls")),

    # prefix endpoints for the admin app with admin/ and use default admin router
    path("admin/", admin.site.urls),

    # prefix endpoints for the accounts app with accounts/
    path("accounts/", include('accounts.urls'))
]
