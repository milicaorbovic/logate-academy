#Create Article

######Used to create new Article in table Articles.

URL : /articles

Method : POST

**Authentication required : YES**
**Role: DEVELOPER**
**User from db with this role: mirko


Data constraints

{
    "title": "[string]",
    "employee_id": "[number]",
    "published_at": "[JSON date format]",
    "document_id": "[number]"
}

Data example

{
    "title": "Mark Down script",
    "employee_id": 1,
    "published_at": "2018-07-04T18:05:43.511Z",
    "document_id": 1
}

Success Response

Code : 200 OK

//Content example

#Update Article

######Used to update Article in table Articles.

URL : /articles

Method : PUT

**Authentication required : YES**
**Role: DEVELOPER**

Data constraints

{
    "id": "[number]",
    "title": "[string]",
    "employee_id": "[number]",
    "published_at": "[JSON date format]",
    "document_id": "[number]"
}

Data example

{
   "id": 4,
    "title": "Mark Down script",
    "employee_id": 1,
    "published_at": "2018-07-04T18:05:43.511Z",
    "document_id": 1
}

Success Response

Code : 200 OK

//Content example


#DeleteArticle

######Used to delete Article in table Articles.

URL : /articles/{id}

Method : DELETE

**Authentication required : YES**
**Role: ADMIN**
**User from db with this role: markov


Data constraints

{
    "id": "[article id]"
}

Data example

{
   "id": 4
}

Success Response

Code : 200 OK

//Content example

#Get Article

######Used to get all Articles from table Articles.

URL : /articles

Method : GET

**Authentication required : YES**
**Role: USER**
**User from db with this role: milica - > pass: mm138138

Success Response

Code : 200 OK

//Content example

#Get Article by ID

######Used to get Article from table Articles by ID

URL : /articles/{id}

Method : GET

**Authentication required : YES**
**Role: USER**
**User from db with this role: milica - > pass: mm138138

Success Response

Code : 200 OK

//Content example

#Post Comments

######Used to post Comments to table Comments.

URL : /comments

Method : POST

**Authentication required : YES**
**Role: any authenticated user can comment


Data constraints

{
    "article_id": "[number]",
    "body": "[string]",
    "published_at": "[JSON date format]",
    "likes": "[number]",
    "dislikes": "[number]",
    "user_id": "[number]"
}

Data example

{
    "article_id": 1,
    "body": "Some comments",
    "published_at": "2018-07-04T18:05:43.511Z",
    "likes": "3",
    "dislikes": "5",
    "user_id": "1"
}

Success Response

Code : 200 OK
//Content example

#Update Comments

######Used to update Comments to table Comments.

URL : /comments/{id}

Method : PUT

**Authentication required : YES**
**Role: only user who posted the comment can edit it


Data constraints

{
    "article_id": "[number]",
    "body": "[string]",
    "published_at": "[JSON date format]",
    "likes": "[number]",
    "dislikes": "[number]",
    "user_id": "[number]"
}

Data example

{
	"article_id": 1,
	"body": "Good",
	"published_at": "2018-07-04T18:05:43.511Z",
	"likes": 3,
	"dislikes": 3,
	"user_id": 2
}

Success Response

Code : 200 OK

//Content example

#Delete Comments

######Used to delete Comments from table Comments.

URL : /comments/{id}

Method : DELETE

**Authentication required : YES**
**Role: only user who posted the comment can delete it or ADMIN Role


Data constraints

{
    "id": "[number]"
}

Data example

{
        "id": 2
}

Success Response

Code : 200 OK

//Content example

#Delete All Comments by Article ID

######Used to delete All Comments by Article ID.

URL : /articles/{id}/comments

Method : DELETE

**Authentication required : YES**
**Role: ADMIN


Data constraints

{
    "id": "[id]"
}

Data example

{
	"id": 3
}

Success Response

Code : 200 OK

//Content example

#Update Comments Likes

######Used to update Comments Likes to table Comments.

URL : /comments/{id}/likes

Method : PUT

**Authentication required : NO**
**Role: Any


Data constraints

{
    "id": "[id]",
    "article_id": "[number]",
    "body": "[string]",
    "published_at": "[JSON date format]",
    "likes": "[number]",
    "dislikes": "[number]",
    "user_id": "[number]"
}

Data example

{
	"id": 1,
	"article_id": 1,
	"body": "Good",
	"published_at": "2018-07-04T18:05:43.511Z",
	"likes": 3,
	"dislikes": 3,
	"user_id": 2
}

Success Response

Code : 200 OK

//Content example

#Update Comments Dislikes

######Used to update Comments dislikes to table Comments.

URL : /comments/{id}/dislikes

Method : PUT

**Authentication required : NO**
**Role: Any


Data constraints

{
    "id": "[id]",
    "article_id": "[number]",
    "body": "[string]",
    "published_at": "[JSON date format]",
    "likes": "[number]",
    "dislikes": "[number]",
    "user_id": "[number]"
}

Data example

{
	"id": 1,
	"article_id": 1,
	"body": "Good",
	"published_at": "2018-07-04T18:05:43.511Z",
	"likes": 3,
	"dislikes": 3,
	"user_id": 2
}

Success Response

Code : 200 OK

//Content example

#Create Roles

######Used to create new ROLE

URL : /roles

Method : POST

**Authentication required : YES**
**Role: ADMIN


Data constraints

{
    "name": "[name of role]",
   "description": "[description of role]"
}

Data example

{
    "name": "Junior Developer",
    "description": "[junior software developer]"
}

Success Response

Code : 200 OK

//Content example

#Create User

######Used to create new USER

URL : /users

Method : POST

**Authentication required : YES**
**Role: ADMIN


Data constraints

{
     "firstname": "[string]",
     "lasname": "[string]",
     "address": "[string]",
     "age": "[number]",
     "username": "[string]",
     "password": "[string]",
     "email": "[string]"
}

Data example

{
    "firstname": "Milica",
    "lastname": "Orbovic",
    "address": "4 .Jula BB",
    "age": 31,
    "username": "milica2",
    "password": "milica123",
    "email": "milicaorbovic@yahoo.com"
}

Success Response

Code : 200 OK

//Content example