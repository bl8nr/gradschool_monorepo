from django.urls import path

from . import views

urlpatterns = [

    # base route for the crypto dashboard home page
    path('', views.index, name='index'),

    # base route for the users watchlist
    path('watchlist', views.watchlist, name='watchlist'),

    # route to show the data for one crypto currency
    path('coin/<coin_id>', views.coin, name='coin'),

    # route to deliver JSON payload for the charting library
    path('coin/<coin_id>/chart', views.chart, name='chart'),

    # route to trigger a share of the coin data to an email address
    path('coin/<coin_id>/chart/share', views.shareCoin, name='shareCoin')
]