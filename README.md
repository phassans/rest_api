<h1>REST API Documentation</h1>

The StudentLab REST API lives at the /StudentLab/api/ endpoint and responsds to GET, POST, and DELETE. 

<h2>Services<br><br>

1. Student<br>
2. Course<br>
3. Register</h2>

<p>Note - <br>
1. Every student is created by providing a email, password along with other information. Email & password is required to delete student.<br>
2. Every course is created by providing professor email, password along with other information. Professor email and password is required to delete course.</p>

<h2>Student Resource - </h2>

<h3>GET - </h3>

Return all students in the system.

<b>/StudentLab/api/students</b>
<pre>
{
    "code": "200",
    "status": "success",
    "message": "Student data retrieved successfully",
    "count": 2,
    "data": [
        {
            "FIRSTNAME": "Mark",
            "LASTNAME": "Anthony",
            "GENDER": "Male",
            "DOB": "1970-01-01 00:00:00.0",
            "EMAIL": "mark@live.com",
            "PHONE": "9701234567",
            "ADDRESS": "123 Street",
            "CITY": "DENVER",
            "STATE": "CO",
            "ZIP": 80123,
            "COUNTRY": "USA"
        },
        {
            "FIRSTNAME": "Pramod",
            "LASTNAME": "Shashidhara",
            "GENDER": "Male",
            "DOB": "1988-04-02 00:00:00.0",
            "EMAIL": "pramodhs@live.com",
            "PHONE": "4252337574",
            "ADDRESS": "1939 S Quebec Way",
            "CITY": "DENVER",
            "STATE": "CO",
            "ZIP": 80231,
            "COUNTRY": "USA"
        }
    ]
}
</pre>

<h3>POST - </h3>

Add a new student into system.

<b>/StudentLab/api/students</b>
<pre>
{
    "FIRSTNAME": "Rob",
    "LASTNAME": "Hill",
    "DOB_DAY": "10",
    "DOB_MONTH": "October",
    "DOB_YEAR": "2000",
    "EMAIL": "rob@live.com",
    "PASSWORD": "123456",
    "PHONE": "4252337574",
    "GENDER": "Male",
    "ADDRESS": "143 S Yale Ave",
    "CITY": "Denver",
    "ZIP": "80521",
    "STATE": "CO",
    "COUNTRY": "USA"
}
</pre>

<h3>DELETE -</h3> 

Removes student from system.

<b>/StudentLab/api/students</b>

<pre>
{
    "EMAIL": "rob@live.com",
    "PASSWORD": "123456"
}
</pre>

<h3>GET -</h3>

Get all courses registered by the student.

<b>/StudentLab/api/students/{email}</b>

<pre>
{
    "code": "200",
    "status": "success",
    "message": "Student data retrieved successfully",
    "count": 2,
    "data": [
        {
            "NAME": "ALGORITHMS",
            "DESCRIPTION": "In mathematics and computer science, an algorithm is a self-contained step-by-step set of operations to be performed"
        },
        {
            "NAME": "Data Structures",
            "DESCRIPTION": "In computer science, a data structure is a particular way of organizing data in a computer so that it can be used efficiently."
        }
    ]
}
</pre>

<h2>Course Resource - </h2>

<h3>GET -</h3> 

Return all courses in the system.

<b>/StudentLab/api/courses</b>

<pre>
{
    "code": "200",
    "status": "success",
    "message": "Course data retrieved successfully",
    "count": 2,
    "data": [
        {
            "ID": 20,
            "CODE": "CS002",
            "NAME": "Data Structures",
            "DEPT": "Computer Science",
            "DESCRIPTION": "In computer science, a data structure is a particular way of organizing data in a computer so that it can be used efficiently."
            "PROFESSOR": "John Hill",
            "PROFESSOR_EMAIL": "pramodhs@live.com",
            "SEATS": 20,
            "TIME": "MWF 2:00-3:30PM",
            "REGDATE": "2015-01-01 00:00:00.0",
            "STARTDATE": "2015-07-01 00:00:00.0"
        },
        {
            "ID": 21,
            "CODE": "CS001",
            "NAME": "ALGORITHMS",
            "DEPT": "Computer Science",
            "DESCRIPTION": "In mathematics and computer science, an algorithm is a self-contained step-by-step set of operations to be performed.",
            "PROFESSOR": "Matthew Bill",
            "PROFESSOR_EMAIL": "pramodhs@live.com",
            "SEATS": 5,
            "TIME": "MWF 2:00-3:30PM",
            "REGDATE": "2015-01-01 00:00:00.0",
            "STARTDATE": "2015-07-01 00:00:00.0"
        }
    ]
}
</pre>

<h3>POST -</h3> 

Add course into system

<b>/StudentLab/api/courses</b>

<pre>
{
    "CODE": "BIO001",
    "NAME": "Anatomy",
    "DESCRIPTION": "Anatomy is the branch of biology concerned with the study of the structure of organisms and their parts; it is mainly divided into zootomy and phytotomy.",
    "DEPT": "Biology",
    "PROFESSOR": "Michael N. Dawson",
    "PROFESSOR_EMAIL": "michael@live.com",
    "PROFESSOR_PWD": "123456",
    "SEATS": "15",
    "TIME": "MWF 2:00-3:30PM",
    "REGDATE_DAY": "1",
    "REGDATE_MONTH": "May",
    "REGDATE_YEAR": "2015",
    "STARTDATE_DAY": "1",
    "STARTDATE_MONTH": "June",
    "STARTDATE_YEAR": "2015",
    "INFO": ""
}
</pre>

<h3>DELETE - </h3>

Delete course from system

<b>/StudentLab/api/courses</b>

<pre>
{
    "PROFESSOR_EMAIL": "michael@live.com",
    "PROFESSOR_PWD": "123456",
    "CODE": "BIO001"
}
</pre>

<h3>GET -</h3> 

Get all students of a course, available seats, seats registered and last day of registration.

<b>/StudentLab/api/students/{coursecode}</b>

<pre>
{
    "code": "200",
    "status": "success",
    "message": "Student data retrieved successfully",
    "count": 2,
    "data": [
        {
            "FIRSTNAME": "Mark",
            "LASTNAME": "Anthony",
            "EMAIL": "mark@live.com"
        },
        {
            "FIRSTNAME": "Pramod",
            "LASTNAME": "Shashidhara",
            "EMAIL": "pramodhs@live.com"
        }
    ],
    "available_seats": 3,
    "seats_registered": 2,
    "last_day_for_registration": "2015-01-01 00:00:00"
}
</pre>

<h2>Register Resource -</h2>

<h3>POST -</h3> 

Register for a course - 

<b>StudentLab/api/register</b>

<pre>
{
    "EMAIL": "pramodhs@live.com",
    "PASSWORD": "123456",
    "COURSE_ID": "20"
}
</pre>

<h3>DELETE - </h3>

UnRegister from a course - 

<b>StudentLab/api/register</b>

<pre>
{
    "EMAIL": "pramodhs@live.com",
    "PASSWORD": "123456",
    "COURSE_ID": "20"
}
</pre>


----<h3>Thanks


