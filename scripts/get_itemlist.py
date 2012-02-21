import urllib
import json
import os

from BeautifulSoup import BeautifulSoup

data_dir_prefix = "data/items"
output_file_name = 'lol-itemlist.json'

wikia_uri = 'http://leagueoflegends.wikia.com'
item_list_uri = '/wiki/Recommended_item_code'

try:
	os.mkdir(data_dir_prefix)
except:
	pass

web_page = urllib.urlopen(wikia_uri + item_list_uri).read()

soup = BeautifulSoup(web_page)

# There are two tables; the toc and the item list.
table = soup.findAll('table')[1]

# just gets item image for now. in gif format.
def get_item_details(item_uri, item_name):
	print item_uri
	fixed_name = item_name.replace(' ','_').replace(':','_')
	img_file_name = data_dir_prefix + fixed_name + '.gif'
	soup = BeautifulSoup(urllib.urlopen(item_uri).read())
	infobox = soup.find('table', 'infobox')
	urllib.urlretrieve(infobox.a['href'], img_file_name)
	return fixed_name + '.gif'
	#item_details = {}
	#soup = BeautifulSoup(urllib.urlopen(item_uri).read())
	#infobox = soup.find('table', 'infobox')
	#item_details['image'] = urllib.urlopen(infobox.a['href']).read()
	#return item_details['image']


item_list = []

rows = table.findAll('tr')[1:]
for row in rows: 
	details = {}
	tds = row.findAll('td')
	details['code'] = tds[0].b.text
	details['link'] = tds[1].a['href']
	details['name'] = tds[1].a['title']
	details['mode'] = tds[2].text
	details['image'] = get_item_details(wikia_uri + details['link'], details['name'])
	item_list.append(details)

import codecs
output_file = codecs.open(output_file_name,'w',encoding='utf-8')
output_file.write(json.dumps(item_list, sort_keys=True, indent=4))
output_file.close()
