text_pwd = "/Users/mkkim/Desktop/pyExample/company_daily_rate/"

read_kospi = text_pwd + "2023-1-22-KOSPI.txt"
read_kosdaq = text_pwd + "2023-1-22-KOSDAQ.txt"

write_kospi = text_pwd + "2023-1-22-KOSPIConvToJsArray.txt"
write_kosdaq = text_pwd + "2023-1-22-KOSDAQConvToJsArray.txt"

text_list = [[read_kospi, write_kospi], [read_kosdaq, write_kosdaq]]

for read_el, write_el in text_list:
    # for use javascript array
    js_arrays = []    
    with open(read_el, "r") as file:
        
        for line in file:
            line = line.split(" ")[1]
            line_split = line.strip().split("/")
            
            #js_arrays.append(["\"" + item + "\"" for item in line_split])
            js_arrays.append([item for item in line_split])

    with open(write_el, "w") as file:
        js_arrays_len = len(js_arrays)
        for idx, item in enumerate(js_arrays):
            if idx == js_arrays_len - 1:
                file.write(str(item))
            else:
                file.write(str(item) + ",\n")