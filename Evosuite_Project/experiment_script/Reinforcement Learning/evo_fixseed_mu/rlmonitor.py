import os
import time

def check_log_file():
    log_file = 'log.txt'
    timeout_minutes = 3
    last_modified = os.path.getmtime(log_file)


    while True:
        time.sleep(60)  

        current_modified = os.path.getmtime(log_file)
        timeout_minutes=timeout_minutes-1
        if current_modified != last_modified:
            timeout_minutes = 3
            last_modified = os.path.getmtime(log_file)
        
        if timeout_minutes == 0:
            timeout_minutes=3

            os.system('taskkill -f -im java.exe')
            print("killed")
            
        
        print(timeout_minutes)

check_log_file()
