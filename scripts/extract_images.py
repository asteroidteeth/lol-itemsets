import os
import json
import re


from shutil import copy

client_location = 'C:\\Riot Games\\League of Legends'

json_output_file = '..\\lol-itemsets-lib\\gamedata\\champ-list.json'
data_dir_prefix = '..\\lol-itemsets-lib\\gamedata\\champs'

name_pattern = re.compile(r'([A-Z][a-z]+)')

try:
	os.makedirs(data_dir_prefix)
except:
	pass

def add_space(champ_name):
	return ' '.join(re.findall(name_pattern, champ_name))

# Only works if the versions all begin with 0.0.0.
# Improve?
def get_latest(versions):
	vers = [map(int,x.split('.')) for x in versions]
	highest = vers[0]
	for ver in vers[1:]:
		if ver[-1] > highest[-1]:
			highest = ver
	return '.'.join(map(str,highest))

def copy_champion_images(client_location):
	first_half = 'RADS\\projects\\lol_air_client\\releases'
	second_half = 'deploy\\assets\\images\\champions'
	versions = os.listdir(os.path.join(client_location,first_half))
	highest = get_latest(versions)

	img_list = os.listdir(os.path.join(client_location,
		                           first_half,highest,second_half))
	img_list = filter(lambda x: '_square_' in x or '_Square_' in x, img_list)

	champs = []
	for img in img_list:
		img_path = os.path.join(client_location,first_half,
				        highest,second_half,img) 
		new_path = os.path.join(data_dir_prefix, img)
		copy(img_path, new_path)
		champ_name = img.split('_')[0]
		champs.append({'name':add_space(champ_name),'face':new_path})
	
	f_out = open(json_output_file, 'w')
	f_out.write(json.dumps(champs, sort_keys=True, indent=4))
		


copy_champion_images(client_location)

