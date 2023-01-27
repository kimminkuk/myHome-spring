with open("/Users/mkkim/Desktop/pyExample/company_daily_rate/2023-1-22-DailyDate.txt", "r") as file:
#with open("/Users/mkkim/Desktop/pyExample/company_daily_rate/2023-1-22-DailyRate.txt", "r") as file:
    # for use javascript array
    js_arrays = []
    
    for line in file:
        line = line.split(" ")[1]
        line_split = line.strip().split("/")
        
        #js_arrays.append(["\"" + item + "\"" for item in line_split])
        js_arrays.append([item for item in line_split])

with open("/Users/mkkim/Desktop/pyExample/company_daily_rate/DailyDateConvToJsArray.txt", "w") as file:
#with open("/Users/mkkim/Desktop/pyExample/company_daily_rate/DailyRateConvToJsArray.txt", "w") as file:    
    js_arrays_len = len(js_arrays)
    for idx, item in enumerate(js_arrays):
        if idx == js_arrays_len - 1:
            file.write(str(item))
        else:
            file.write(str(item) + ",\n")
            
# js_arrays_len = len(js_arrays)
# for idx, item in enumerate(js_arrays):
#     if idx == js_arrays_len - 1:
#         print(item, end="")
#     else:
#         print(item, end=",\n")
