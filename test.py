import httplib
import urllib
import time
from multiprocessing.pool import ThreadPool

def test_GET_200():
	beforeTime = time.time()
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('GET', '/index.html')
	response = connection.getresponse()
	status = response.status
	data = response.read()
	afterTime = time.time()
	timetaken = str((afterTime - beforeTime)*1000)
	if status == 200 and len(data) != 0:
		print "Test Case Passed: " + test_GET_200.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_GET_200.__name__

def test_GET_404():
	beforeTime = time.time()
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('GET', '/random.html')
	status = connection.getresponse().status
	afterTime = time.time()
	timetaken = str((afterTime - beforeTime)*1000)
	if status == 404:
		print "Test Case Passed: " + test_GET_404.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_GET_404.__name__

def test_HEAD_200():
	beforeTime = time.time()
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('HEAD', '/index.html')
	response = connection.getresponse()
	status = response.status
	data = response.read()
	afterTime = time.time()
	timetaken = str((afterTime - beforeTime)*1000)
	if status == 200 and len(data) == 0:
		print "Test Case Passed: " + test_HEAD_200.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_HEAD_200.__name__

def test_HEAD_404():
	beforeTime = time.time()
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('HEAD', '/random.html')
	status = connection.getresponse().status
	afterTime = time.time()
	timetaken = str((afterTime - beforeTime)*1000)
	if status == 404:
		print "Test Case Passed: " + test_HEAD_404.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_HEAD_404.__name__

def test_POST_501():
	beforeTime = time.time()
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('POST', '/index.html')
	status = connection.getresponse().status
	afterTime = time.time()
	timetaken = str((afterTime - beforeTime)*1000)
	if status == 501:
		print "Test Case Passed: " + test_POST_501.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_POST_501.__name__

def sendGET():
	connection = httplib.HTTPConnection('127.0.0.1:8000')
	connection.request('GET', '/index.html')
	response = connection.getresponse()
	return response.status == 200

def test_multithreaded():
	beforeTime = time.time()
	pool = ThreadPool(processes=2)
	async_result1 = pool.apply_async(sendGET())
	async_result2 = pool.apply_async(sendGET())
	
	a = async_result1.get()
	b = async_result2.get()
	if a is True and b is True:
		afterTime = time.time()
		print "Test Case Passed: " + test_multithreaded.__name__ + " (" + timetaken + "ms)"
	else:
		print "Test Case Failure: " + test_multithreaded.__name__
		

# run test Cases
test_GET_200()
test_GET_404()
test_HEAD_200()
test_HEAD_404()
test_POST_501()
#test_multithreaded()