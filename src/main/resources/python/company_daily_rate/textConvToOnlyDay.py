text_pwd = "/Users/mkkim/Desktop/pyExample/company_daily_rate/"
read_full_date_text = text_pwd + "fullDateText.txt"
write_only_day_text = text_pwd + "onlyDayText.txt"
write_only_day_text_ver2 = text_pwd + "onlyDayTextVer2.txt"

wrtie_file_list = [write_only_day_text, write_only_day_text_ver2]

full_date_data = ['2023.01.20', '2023.01.19', '2023.01.18', '2023.01.17', '2023.01.16', '2023.01.13', '2023.01.12', '2023.01.11', '2023.01.10', '2023.01.09', '2023.01.06', '2023.01.05', '2023.01.04', '2023.01.03', '2023.01.02', '2022.12.29', '2022.12.28', '2022.12.27', '2022.12.26', '2022.12.23', '2022.12.22', '2022.12.21', '2022.12.20', '2022.12.19', '2022.12.16', '2022.12.15', '2022.12.14', '2022.12.13', '2022.12.12', '2022.12.09', '2022.12.08', '2022.12.07', '2022.12.06', '2022.12.05', '2022.12.02', '2022.12.01', '2022.11.30', '2022.11.29', '2022.11.28', '2022.11.25', '2022.11.24', '2022.11.23', '2022.11.22', '2022.11.21', '2022.11.18', '2022.11.17', '2022.11.16', '2022.11.15', '2022.11.14', '2022.11.11', '2022.11.10', '2022.11.09', '2022.11.08', '2022.11.07', '2022.11.04', '2022.11.03', '2022.11.02', '2022.11.01', '2022.10.31', '2022.10.28', '2022.10.27', '2022.10.26', '2022.10.25', '2022.10.24', '2022.10.21', '2022.10.20', '2022.10.19', '2022.10.18', '2022.10.17', '2022.10.14', '2022.10.13', '2022.10.12', '2022.10.11', '2022.10.07', '2022.10.06', '2022.10.05', '2022.10.04', '2022.09.30', '2022.09.29', '2022.09.28', '2022.09.27', '2022.09.26', '2022.09.23', '2022.09.22', '2022.09.21', '2022.09.20', '2022.09.19', '2022.09.16', '2022.09.15', '2022.09.14', '2022.09.13', '2022.09.08', '2022.09.07', '2022.09.06', '2022.09.05', '2022.09.02', '2022.09.01', '2022.08.31', '2022.08.30', '2022.08.29', '2022.08.26', '2022.08.25', '2022.08.24', '2022.08.23', '2022.08.22', '2022.08.19', '2022.08.18', '2022.08.17', '2022.08.16', '2022.08.12', '2022.08.11', '2022.08.10', '2022.08.09', '2022.08.08', '2022.08.05', '2022.08.04', '2022.08.03', '2022.08.02', '2022.08.01', '2022.07.29', '2022.07.28', '2022.07.27', '2022.07.26', '2022.07.25', '2022.07.22', '2022.07.21', '2022.07.20', '2022.07.19', '2022.07.18', '2022.07.15', '2022.07.14', '2022.07.13', '2022.07.12', '2022.07.11', '2022.07.08', '2022.07.07', '2022.07.06', '2022.07.05', '2022.07.04', '2022.07.01', '2022.06.30', '2022.06.29', '2022.06.28', '2022.06.27', '2022.06.24', '2022.06.23', '2022.06.22', '2022.06.21', '2022.06.20', '2022.06.17', '2022.06.16', '2022.06.15', '2022.06.14', '2022.06.13', '2022.06.10', '2022.06.09', '2022.06.08', '2022.06.07', '2022.06.03', '2022.06.02', '2022.05.31', '2022.05.30', '2022.05.27', '2022.05.26', '2022.05.25', '2022.05.24', '2022.05.23', '2022.05.20', '2022.05.19', '2022.05.18', '2022.05.17', '2022.05.16', '2022.05.13', '2022.05.12', '2022.05.11', '2022.05.10', '2022.05.09', '2022.05.06', '2022.05.04', '2022.05.03', '2022.05.02', '2022.04.29', '2022.04.28', '2022.04.27', '2022.04.26', '2022.04.25', '2022.04.22', '2022.04.21', '2022.04.20', '2022.04.19', '2022.04.18', '2022.04.15', '2022.04.14', '2022.04.13', '2022.04.12', '2022.04.11', '2022.04.08', '2022.04.07', '2022.04.06', '2022.04.05']


data = []
data_ver2 = []

data = [item[-8:] for item in full_date_data]
data_ver2 = [item[-5:] for item in full_date_data]



for write_el, data_el in zip(wrtie_file_list, [data, data_ver2]):
    with open(write_el, "w") as file:
        for item in data_el[ : -1]:
            file.write( "'" + item + "', ")
        file.write("'" + data_el[-1] + "'")
        
        