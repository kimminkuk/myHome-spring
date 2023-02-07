#1 2023.01.20


#
#    1. 두개의 텍스트 파일의 이름을 비교하고,
#    2. 두개 중 날짜가 더 느린 파일의 내용을 더 빠른 날짜의 파일에 추가한다.
#    3. 빠른날짜의 파일의 행들의 열에 느린 날짜의 파일들의 행들의 열을 이어 붙인다.
#    

#    0. resultText(가제), newText(가제)
#    1. reulstText의 삼성전자 index의 첫번째 배열 element값을 읽어와서, resultText의 날짜를 가져온다.
#    2. newText를 만들 때, 날짜가 resultText의 첫번쨰 배열보다 같거나 작으면 안된다.
#    3. newText의 텍스트 내용들과, resultText 내용들을 이어 붙인다.

#    이걸 JAVA로 어떻게 자동화 할까 흠..




#    간단 테스트1) 
#  
#    step1) 임시로 간단한 파일을 만든다.
#    step2) 현재 있는 날짜데이터의 파일을 읽어온다.
#    step3) step2가 더 크니깐.. step1의 파일 내용을 step2에 추가하는게 좀 더 효율적이겠지?
text_pwd = "/Users/mkkim/Desktop/pyExample/company_daily_rate/"
#read_date_text = text_pwd + "DailyDateConvToJsArray.txt"
write_date_text = text_pwd + "tempText.txt"
read_date_text_temp = text_pwd + "tempText.txt"
read_date_text = text_pwd + "2023-1-22-DailyDate.txt"
result_date_text = text_pwd + "2023-1-22-DailyDateConvToJsArray.txt"
# step1)
# temp_date = '2023.01.21'
# file_max_line = 2437
# with open(write_date_text, "w") as file:
#     for i in range(file_max_line):
#         file.write( str(i+1)  + " " +temp_date + "\n")
        
# step2)

temp_text_list = []
with open(read_date_text_temp, "r") as file:
    for line in file:
        temp_text_list.append(line.split(" ")[1])

read_text_list = []
with open(read_date_text, "r") as file:
    for line in file:
        read_text_list.append(line.split(" ")[1])

result_text_list = [] 
cnt = 1
for read_text_el, read_temp_el in zip(read_text_list, temp_text_list):
    result_text_list.append( str(cnt) + " " + read_temp_el.strip() + '/' + read_text_el)
    cnt+=1
        
with open(result_date_text, "w") as file:
    for idx, line in enumerate(result_text_list):
        line = line.split(" ")[1]
        line_split = line.strip().split("/")
        if idx == len(result_text_list) - 1:
            file.write(str(line_split))
        else:
            file.write(str(line_split) + ",\n")
            
            