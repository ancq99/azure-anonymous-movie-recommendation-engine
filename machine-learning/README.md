# Description 
Our original idea was to use the Wide and Deep model, but we had a lot of problems with it. The two main problems were: very poor documentation and returning the same movies over and over again as a result of the prediction, regardless of the data in the input.

We therefore relied on a simpler solution, namely counting the distances between movies using Manhattan distances.
As a feature vector, we only pledged user reviews, which rated the movie with 5 stars, and the crew, and cast appearing in the movie.

# Creating the model

To do this, run the model notebook. It will create a pickle file, which will be registered with Azure Machine Learnig Studio.

In case you do not want to teach the entire model from scratch, you can load the pickle file (similarity_df.pkl)
Load it into notepad and go straight to registration.


# Creating Endpoint 
At MLStudio, we are creating a new ASK.

![askcompute](https://user-images.githubusercontent.com/57688356/210434701-50b86d27-6cfc-4edc-b96b-c2b4e6869f42.png)

We choose the vm.

![endpoint_azure](https://user-images.githubusercontent.com/57688356/210434264-c03b3b8f-4c83-4cc9-8d9f-10c0b28d7fd5.png)

Then go to the Models tab, select the created model (model_rec) and click Deploy.

![obraz](https://user-images.githubusercontent.com/66008982/202914226-5a7ba8bc-f38f-4c97-a336-c8d77feed129.png)


Select Deploy Web Service, fill in the fields (Entry Script -> azure/score.py, Conda dependencies -> azure/conda.yaml) and click Deploy. 

![obraz](https://user-images.githubusercontent.com/66008982/202914394-9d21d227-525b-4685-b958-e969064b12b2.png)



Once the implementation is complete, we can find the details of the endpoint in the Endpoints tab. 

# Example of data
* Input - movies we like in the format -> ["title1", "title2", "title3", ...].
* Output - recommended movies for titles in the format -> ["title1", "title2", "title3", ...].K


# Update models 
Python modules machine-learning/schedulejob/update_model.py creates a model that stores in blob storage and endpoint (machine-learning/endpoint/score.py) downloads the latest model file once a day.

# Create schedule job for update model 


Use data factory to create a schedule job 
according to the tutorial (https://learn.microsoft.com/en-us/azure/batch/tutorial-run-python-batch-azure-data-factory)
Load python module update_modeles.py in data factory job and create schedule trigger once a day.


# Endpoint has been updated:
- Downloading a new model once a day 
- Endpoint does not connect to the database, using only the model file (saving endpoint memory so saving money ) 
- Endpoint works faster



