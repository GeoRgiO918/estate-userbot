from flask import Flask, request, jsonify
import tensorflow as tf
import pickle
from tensorflow.keras.preprocessing.sequence import pad_sequences

app = Flask(__name__)

# Загрузка модели и токенизатора
model = tf.keras.models.load_model("lead_classifier_model.keras")
with open("tokenizer.pickle", "rb") as f:
    tokenizer = pickle.load(f)

MAXLEN = 100

def preprocess(text):
    seq = tokenizer.texts_to_sequences([text])
    padded = pad_sequences(seq, maxlen=MAXLEN)
    return padded

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    text = data.get("text", "")
    input_tensor = preprocess(text)
    pred = float(model.predict(input_tensor)[0][0])
    label = int(pred >= 0.5)
    return jsonify({"lead": label, "probability": pred})