from django.shortcuts import render, redirect
from pycoingecko import CoinGeckoAPI
from django.views.decorators.http import require_http_methods
from django.contrib.auth.decorators import login_required
from django.core.mail import send_mail
from django.http import JsonResponse
from .models import Watchedcoin

cg = CoinGeckoAPI()

@login_required()
def index(request):
    coinlist = cg.get_coins_markets(vs_currency='usd')

    # create a context with crypto data in it
    context = {
        "user": {
            "watchlist": request.user.watchlist,
            "username": request.user
        },
        "coinlist": coinlist,
        "isWatchlist": False
    }

    # render the tracker page (it like a home/dashboard page)
    return render(request, "tracker.html", context)

@login_required()
@require_http_methods(["POST", "GET"])
def watchlist(request):

    # if the request is a POST then the user is adding another coin to their watchlist
    if request.method == "POST":
        coinData = getCoinInfoFromMasterList(request.POST.get('coinId', ''))

        if request.POST.get('method', '') == 'post':
            watchedcoin = Watchedcoin(
                            coinId=coinData["id"],
                            coinSymbol=coinData["symbol"],
                            coinImage=coinData["image"],
                            coinName=coinData["name"],
                            user=request.user)
            watchedcoin.save()

        if request.POST.get('method', '') == 'delete':
            watchedcoin = Watchedcoin.objects.filter(user=request.user, coinId=coinData["id"])[0]
            watchedcoin.delete()

        return redirect("coin", coin_id=coinData["id"])

    # construct a list of coins and their data from the ids in the users watchlist
    coinlist = []
    for coin in request.user.watchlist.all():
        coinlist.append(getCoinInfoFromMasterList(coin.coinId))

    # create a context with users watched coins in it
    context = {
        "user": {
            "watchlist": request.user.watchlist,
            "username": request.user
        },
        "coinlist": coinlist,
        "isWatchlist": True
    }

    # render the tracker page (it like a home/dashboard page)
    return render(request, "tracker.html", context)

@login_required()
def coin(request, coin_id):
    coin = cg.get_coin_by_id(id=coin_id)
    chart = cg.get_coin_market_chart_by_id(id=coin_id, vs_currency="usd", days=356)
    is_watched = False

    for c in request.user.watchlist.all():
        if c.coinId == coin_id:
            is_watched = True

    # create context with a coins crypto data in it
    context = {
        "user": {
            "watchlist": request.user.watchlist,
            "username": request.user
        },
        "coin": coin,
        "isWatched": is_watched,
        "chart": chart
    }

    # render the tracker page (it like a home/dashboard page)
    return render(request, "coin.html", context)

def chart(request, coin_id):
    chart = cg.get_coin_market_chart_by_id(id=coin_id, vs_currency="usd", days=356)

    return JsonResponse(chart)

def shareCoin(request, coin_id):
    emailAddress = request.POST.get('email', '');
    message = request.POST.get('message', '');
    print(request.user)
    send_mail(
        "subject",
        message + 'http://127.0.0.1:8000/tracker/coin/' + coin_id,
        'donotreply@cryptotracker341231.com',
        [emailAddress],
        fail_silently=False,
    )

    return JsonResponse({"Ssccess": True})


def getCoinInfoFromMasterList(coinId):
    for c in cg.get_coins_markets(vs_currency='usd'):
        if c['id'] == coinId:
            return c