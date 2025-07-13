from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np
import json
import pickle

from tensorflow.keras.preprocessing.sequence import pad_sequences

app = Flask(__name__)

# Загрузи модель
model = tf.keras.models.load_model("lead_classifier_model.keras")  # или model.h5

# Загрузка токенизатора
with open("tokenizer.pickle", "rb") as f:
    tokenizer = pickle.load(f)

MAXLEN = 100  # длина, с которой ты тренировался

def preprocess(text):
    sequence = tokenizer.texts_to_sequences([text])
    padded = pad_sequences(sequence, maxlen=MAXLEN)
    return padded

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    text = data.get("text", "")
    input_tensor = preprocess(text)
    prediction = model.predict(input_tensor)[0][0]
    label = int(prediction >= 0.5)
    return jsonify({"lead": label, "probability": float(prediction)})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
