from rest_framework.decorators import api_view
from rest_framework.response import Response

from PIL import Image
import os, glob, numpy as np
from keras.models import load_model

directory = os.getcwd()
model = load_model(directory + '/keras_drf/multi_img_classification.model')

@api_view(['POST'])
def keras(request):

    X = []
    files = request.data['image']
    img = Image.open(files)
    img = Image.open(files)
    # img = img.convent("RGB") # 왜 에러인지 모르겠다
    img = img.resize((64, 64))
    data = np.asarray(img)
    X.append(data)
    X = np.array(X)
    prediction = model.predict(X)
    pre_ans = prediction[0].argmax()
    if pre_ans == 0: 
        pre_ans_str = "고등어태비"
    else: 
        pre_ans_str = "치즈태비"
    if prediction[0][0] >= 0.8 : 
        print("해당 이미지는 " + pre_ans_str + "로 추정됩니다.")
    elif prediction[0][1] >= 0.8: 
        print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
    res = pre_ans_str
    return Response(res)