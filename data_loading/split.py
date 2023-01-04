import json

filenames = 'Movies_all.json'
cnt = 0

f = open(filenames)

data = json.load(f)
cast_file = open('cast.json', 'w')
crew_file = open('crew.json', 'w')
rating_file = open('rating.json', 'w')
info_file = open('info.json', 'w')

index = 0
for movie_id in data:
    info = data[movie_id]['info']

    if len(info)==0:
        continue

    rating = data[movie_id]['ratings']
    numofvotes= len(rating)

    if numofvotes==0:
        continue

    sum = 0
    for ratinglist in rating:
        rating[ratinglist]['ratinglistfk']=index+1
        sum = sum + float(rating[ratinglist]['rating'])
        rating_file.writelines(json.dumps(rating[ratinglist])+'\n')

    avgrating = sum/numofvotes

    cast = data[movie_id]['cast']
    castnumber= len(cast)
    for castlist in cast:
        cast[castlist]['castlistfk']=index+1
        cast_file.writelines(json.dumps(cast[castlist])+'\n')

    crew = data[movie_id]['crew']
    crewnumber= len(crew)
    for crewlist in crew:
        crew[crewlist]['crewlistfk']=index+1
        crew_file.writelines(json.dumps(crew[crewlist])+'\n')
        
    

    info['movieid']=index+1
    info['castnumber']=castnumber
    info['crewnumber']=crewnumber
    info['numofvotes']=numofvotes
    info['avgrating']=avgrating

    info_file.writelines(json.dumps(info)+'\n')

    index = index+1


