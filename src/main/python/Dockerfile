# Базовый образ с Python
FROM python:3.11-slim

# Рабочая директория
WORKDIR /app

# Копируем зависимости
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Копируем скрипты и данные
COPY train_model.py app.py lead_messages.csv ./

# Генерируем модель при билде
RUN python train_model.py

# Открываем порт
EXPOSE 5000

# Запуск Flask через Gunicorn с несколькими воркерами
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "--workers", "4", "--threads", "2", "app:app"]