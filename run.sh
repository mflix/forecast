git pull
kill -15 $(lsof -i:8080 -t)
rm -rf nohup.out
touch nohup.out
nohup gradle bootRun &
tail -f nohup.out
