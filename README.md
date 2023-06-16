# AdaJob
AdaJob is a mobile application to recommend Airdrop Cryptography jobs. 
In this application we will provide lists of available Airdrop jobs and give some Airdrop jobs recommendations to each user. 
To give recommendation to each user, we use Machine Learning to create the Collaborative Filtering Recommendation System.
The tools used in this project are TensorFlow, TensorFlow Recommenders, and Google Colaboratory.
This repository contains the main parts for the Machine Learning model as follows:
* [Dataset Recommendation System](https://github.com/GallantSonara/AdaJob/tree/main/Dataset%20Recommendation%20System) : contains 2 csv files to train the model. 
* [Recommendation Model](https://github.com/GallantSonara/AdaJob/tree/main/Recommender%20Model) : contains Recommender_System.ipynb to train and export the model, Load_savedmodel.ipynb to test the exported model and get the recommendation prediction, and modelsavedwithkerasmeta.zip as the exported model file.

To duplicate our project, download the datasets first and run the Recommender_System.ipynb. The output will be modelsavedwithkerasmeta.zip. 
We refer to [TensorFlow Recommenders' documentation](https://github.com/tensorflow/recommenders/blob/main/docs/examples/basic_retrieval.ipynb) to build our recommendation model.




