import uvicorn
from enum import Enum
from pydantic import BaseModel
import mysql.connector
from fastapi import FastAPI, HTTPException, Path, Query
import tensorflow as tf
import numpy as np
import pandas as pd
import os

app = FastAPI()  # create a new FastAPI app instance


mysql_config = {
    'host': '34.101.131.51',
    'user': 'root',
    'password': '123asd',
    'database': 'auth_db'
}

def get_users(inputid):
  # Establish a connection to the MySQL server
  conn = mysql.connector.connect(**mysql_config)
  cursor = conn.cursor()

  # Execute the query to retrieve all users
  query = "SELECT 'id', 'task_id', 'task_title' FROM users WHERE id = " + inputid + "'";
  cursor.execute(query)

  # Fetch all the user records
  users = cursor.fetchall()

  # Close the cursor and connection
  cursor.close()
  conn.close()

  # Convert the result to an array variable
  users_array = []
  for data in users:
      user_dict = {
          'id': data[0],
          'task_id': data[1],
          'task_title': data[2]
      }
      users_array.append(user_dict)

  return users_array # 1D array
# mode.predict([users_array]) # 2D array


model = tf.keras.models.load_model('./pleasework')
df_enrollments = pd.read_csv('Dataset_Recommendation_System_-_Enrollment_Dataset_enroll_only.csv',  dtype={'user_id': 'str', 'task_id':'str'})

def delete_enrolled_tasks(userid, titles):
  titles = titles.tolist()
  recommended_titles = []
  for i in range(len(titles[0])):
    has_been_enrolled = False
    cleaned_title = str(titles[0][i]).replace('b\'', '')
    cleaned_title = cleaned_title.replace('\'', '')
    for task_enrolled in df_enrollments[df_enrollments['user_id']==str(userid)].task_title:
      if cleaned_title == task_enrolled:
        has_been_enrolled = True
        break
    if not has_been_enrolled: 
      recommended_titles.append(cleaned_title)
      print(cleaned_title)
      #print('a')
  return recommended_titles


@app.get("/")
def hello_world():
    return ("hello world")


# Define a Pydantic model for an item
class Item(BaseModel):
    userID: int

@app.post("/")
def add_item(item: Item):
    _, title = model.predict([str(item.userID)])
    result = delete_enrolled_tasks(item.userID, title)
    return result
