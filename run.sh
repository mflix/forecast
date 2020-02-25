git pull
kill -9 $(lsof -i:8080 -t)
gradle clean
nohup gradle bootRun &
tail -f nohup.out
