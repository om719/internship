# internship
Server
For this task I have stored the pdf files in an xampp server. The names of the files are present in a database on the server itself. 
using file_structure.php I have accessed the contents of the database, i.e. the ids and the names of the files and generated a 
json file which is stored in the server.

Android Studio
I have used a recyclerview to present the list of Pdfs. For this purpose I have created a a separate Adapter and Model Package. This
enables the reading of the code to be simpler and easier as the code is broken into separately functioning chunks.
Using the Volley library in android studio I have parsed the json file in the server and accessed the names. I have then appended the
names to the the path of the file location on the server. This url is passed to the pdfView activity where we are able to view the pdf 
without downloading.
