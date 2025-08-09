@echo off
setlocal

cd python
python model_create.py

:: ����᪠�� �ࢥ� � �⤥�쭮� ����
start cmd /k python classifier_api.py


python wait_server.py
if errorlevel 1 (
    echo ��ࢥ� �� �����⨫��. ��⠭����.
    pause
    exit /b 1
)
cd ..
:: ����� �ࢥ� ��⮢ - ����᪠�� ���
java -jar estate-userbot.jar
