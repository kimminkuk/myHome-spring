import requests
from bs4 import BeautifulSoup
import time

# /opt/homebrew/bin/python3

# company_code_221230.py 에서 가져온다.
import company_code_221230
import company_list_221230

def textContentCheck(text):
    # date와 price의 길이가 0인지 검사하고, 텍스트가 "-" 와 일치하면 "0"으로 치환합니다.
    if len(text) == 0 or text == "-":
        return "0"
    return text

def get_price_data(company_code, date_range):
    result_date = ""
    result_price = ""
    text_tr_idx = [3, 4, 5, 6, 7, 11, 12, 13, 14, 15];
    for date in date_range:
        url = "https://finance.naver.com/item/sise_day.naver?code=" + company_code + "&page=" + str(date)
        response = requests.get(url)
        soup = BeautifulSoup(response.text, "html.parser")

        for tr_el in text_tr_idx:
            search_1 = "body > table.type2 > tbody > tr:nth-child(" + str(tr_el) + ") > td:nth-child(2)"
            search_2 = "body > table.type2 > tbody > tr:nth-child(" + str(tr_el) + ") > td:nth-child(1)"

            dates = soup.select(search_1)
            prices = soup.select(search_2)

            for date, price in zip(dates, prices):

                date = date.text.strip()
                price = price.text.strip()
                date = textContentCheck(date)
                price = textContentCheck(price)
                result_date += date + "/"
                result_price += price + "/"

    return result_date, result_price


def main():
    print("main Call")
    company_codes = company_code_221230.company_code_list
    company_names = company_list_221230.company_names
    date_range = range(1, 21)
    all_price_data = []
    all_date_data = []
    cnt = 3
    for company_code in company_codes:
        if cnt < 0: break
        print(company_code + " call!")
        price_data, price_date =  get_price_data(company_code, date_range)
        all_price_data.append(price_data)
        all_date_data.extend(price_date)
        cnt -= 1

    #all_price_data와 all_date_data의 마지막 요소의 마지막 "/"를 제거합니다.
    all_price_data[-1] = all_price_data[-1][:-1]
    all_date_data[-1] = all_date_data[-1][:-1]

    with open('price_data.txt', 'w') as file:
        for idx, data in enumerate(all_price_data):
            file.write( str(idx + 1) + " " + str(data) + "\n")
    with open('date_data.txt', 'w') as file:
        for idx, data in enumerate(all_date_data):
            file.write( str(idx + 1) + " " + str(data) + "\n")


if __name__ == '__main__':
    start_time = time.time()
    main()
    end_time = time.time()
    print("Time spent parsing: %s seconds" % (end_time - start_time))