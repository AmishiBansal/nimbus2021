1. List of Departments of Quiz
	- /quiz/departments
	- GET
	- {
		- departmentName: '',
		- departmentId: '' 
}

2. List of Quizzes by department
	- /quiz/departments
	- POST
	- Request: {
		- deptId: ''
}
	- Response: [
	{
		- quizName: '',
		- quizId: '',
		- startTime: DATE,
		- endTime: DATE
	}
	]

3. Retrieve Questions
	- /quiz/questions
	- POST
	- Request: {
		- 
		- quizId: ''
}
	- Response: 
		1. {errorCode: 1}	// Time is not right
		2. {errorCode: 2}	// Player has already played
		3. {
		errorCode: 0,
		questions: [
		{
			- questionId: ''
			- questionName: '',
			- option1: 
			- option4:
			}]
	}

4. Post Answer
	- /quiz/submit
	- POST
	- Request: {
	answers: [ {
		- questionId: '',
		- answer: INT
}]
	- Response: { score: INT }
}

### Tip for Android: While waiting for response, make a loading screen 

5. Get Leaderboard
	- /quiz/leaderboard
	- POST
	- Request: {
		- quizId: '',
}	
	- Response: [
	{
		- name: '',
		- image: url(string)
	}
	]

__________________________________________________________________-

6. Sign Up
	- /auth/signup
	- POST
	- Request: {
		- name: '',
		- image: ''
		- college: ''
		- phone: INT
		- rollNumber: ''
}
	- Response: {
		- token: '<ObjID>'		// Set this as header for all future requests
		- errorCode: 0		
}							// If everything is valid
	// If there is some problem
	2. {
		- errorCode: 1
}

7. Edit Profile
	- /auth/profile
	- POST
	- Same as Sign Up

8. Get Profile
	- /auth/profile
	- GET
	- Response: {
		- name: '',
		- image: ''
		- campusAmbassador: boolean
		- college: ''
		- phone: INT
		- rollNumber: ''
}

_______________________________________________________________________

9. Add Campus Ambassador
	-/auth/campusAmbassador
	- POST
	- Request: {
		- username: '',
		- password: '',
}
	- Response: {
		- errorCode: INT // 0 for that person is a campus Ambassador
						 // 1 for else
}

10. Campus Ambassador Uploads
	- POST 
	- Request: {
		- url: '',	// check for uniqueness
		- source: ''
}


________________________________________________________________

10. Get Core Team
	- /coreTeam
	- GET
	- Response: {
		- name: '',
		- position: '',
		- image: ''
}

11. Get Department Events
	- /departmentEvents
	- GET
	- Response: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}



12. Get Institute Events
	- /institueEvents
	- GET
	- Response: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}

13. Get Talks
	- /talks
	- GET
	- Response: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}

13. Post Talks 
	- /talks
	- POST
	- Request: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}
	- Response: {
		- message: ''	//success for ok, status code 200
						//failure for fail, any other status code
}

14. Get Exhibitions
	- /exhibitions
	- GET
	- Response: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}

15. Post Exhibition
	- /exhibitions
	- POST
	- Request: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}
	- Response: {
		- message: ''	//success for ok, status code 200
						//failure for fail, any other status code
}

16. Get Workshops
	- /workshops
	- GET
	- Response: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
		- type: INT,
}

17. Post Workshops
	- /workshops
	- POST
	- Request: {
		- name: '',
		- info: '',
		- venue: '',
		- date: DATE,
		- image: '',
		- regUrl: '',
}
	- Response: {
		- message: ''	//success for ok, status code 200
						//failure for fail, any other status code
}

___________________________________________________________________________-