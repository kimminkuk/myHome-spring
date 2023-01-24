import company_konex_list_221230
import company_konex_code_list_221230
import company_list_221230
import company_code_221230

from collections import defaultdict

# 파이썬 클래스 예제 코드
class stockMain:
    def __init__(self):
        self.company_list = company_list_221230.company_names
        self.company_code = company_code_221230.company_code_list
        self.company_konex_list = company_konex_list_221230.company_konex_list
        self.company_konex_code_list = company_konex_code_list_221230.company_konex_code_list
        self.result_company_name_list = []
        self.result_company_code_list = []
        self.result_company_list = []

    def restore_company_code_six_digit(self, restore_company_code_list):
        for idx, company_code in enumerate(restore_company_code_list):
            cur_len = len(company_code)
            if cur_len != 6:
                add_len = 6 - cur_len
                restore_company_code_list[idx] = ( ( '0' * add_len ) + company_code)
        return
        
    def get_company_list(self, all_company, except_company):
        
        company_name_dict = defaultdict(list)
        
        for all_company_name in all_company:
            company_name_dict[all_company_name] = 1        
        
        for except_company_name in except_company:
            company_name_dict[except_company_name] += 1
        
        for idx, company_name in enumerate(all_company):
            if company_name_dict[company_name] == 1:
                self.result_company_name_list.append(self.company_list[idx])
                self.result_company_code_list.append(self.company_code[idx])
                self.result_company_list.append([self.company_list[idx], self.company_code[idx]])
        
        return self.result_company_list, self.result_company_name_list, self.result_company_code_list
    
    def file_save(self, file_name, file_data):
        file = open(file_name, 'w', encoding='utf-8')
        for data in file_data:
            file.write(str(data))
            file.write('\n')
        file.close()
        return
    
    def file_save_ver2(self, file_name, file_data):
        file = open(file_name, 'w', encoding='utf-8')
        file_data_len = len(file_data)
        for idx, data in enumerate(file_data):
            if idx == file_data_len - 1:
                data = '"' + str(data) + '"'
            else:
                data = '"' + str(data) + '"' + ","
            file.write(str(data))
            file.write('\n')
        file.close()
        return
    

my_stock_main = stockMain()
my_stock_main.restore_company_code_six_digit(my_stock_main.company_code)
my_stock_main.restore_company_code_six_digit(my_stock_main.company_konex_code_list)

company_lists, company_names, company_codes = my_stock_main.get_company_list(my_stock_main.company_list, my_stock_main.company_konex_list)

my_stock_main.file_save('company_lists.txt', company_lists)
my_stock_main.file_save_ver2('company_names.txt', company_names)
my_stock_main.file_save_ver2('company_codes.txt', company_codes)