#! /usr/bin/python
# FXN LIBRARY
import MySQLdb
import time
import csv
MySQLdb.paramstyle
from datetime import datetime

## Jonathan Galingan --> https://groups.google.com/a/openmrs.org/d/msg/implementers/Ki8Jk1PX_Qo/bP19QS0s4REJ

## csv parameters
filename = "icdcodes.csv"
reader = csv.reader(open(filename,"rb"), delimiter=',')


## MySQL parameters 
connMRS = MySQLdb.connect (host="localhost",
        port=3306,
        user="root",
        passwd = "root",
        db="openmrs")

## id number of concept class
class_id = '29'



for row in reader:
	cursoromrs1 = connMRS.cursor()
	cursoromrs2 = connMRS.cursor()
	cursoromrs3 = connMRS.cursor()


	concept_name= row[0]
	icd_code = row[1]

	## Querries that insert data into OpenMRS database

	ins_concept_stmnt = """INSERT INTO concept(datatype_id, class_id,creator,date_created,\
	version, uuid) \
	VALUES('4', %s,'1',NOW(), '1', uuid())"""
	cursoromrs1.execute(ins_concept_stmnt, (class_id))
	conceptid = int(cursoromrs1.lastrowid)
	cursoromrs1.execute("commit")
	cursoromrs1.close()



	ins_concept_name_stmnt = """INSERT INTO concept_name(concept_id, name, locale, creator,date_created, \
	uuid, concept_name_type, locale_preferred) \
	VALUES(%s, %s, 'en','1',NOW(), uuid(), 'FULLY_SPECIFIED', '1')"""
	cursoromrs2.execute(ins_concept_name_stmnt,(conceptid, concept_name))
	cursoromrs2.execute("commit")
	cursoromrs2.close()



	ins_map_stmnt = """INSERT INTO concept_map(source, source_code,creator,date_created,\
	concept_id,uuid) \
	VALUES('1', %s,'1',NOW(), %s, uuid())"""
	cursoromrs3.execute(ins_map_stmnt, (icd_code, conceptid))
	cursoromrs3.execute("commit")
	cursoromrs3.close()


