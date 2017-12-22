APP_NAME=kjv-service
systemctl daemon-reload
systemctl stop ${APP_NAME}
systemctl enable ${APP_NAME}
systemctl start ${APP_NAME}
sudo systemctl status ${APP_NAME}