// Initialize Firebase (ADD YOUR OWN DATA)
  const firebaseConfig = {
    apiKey: "AIzaSyBo3zm6YaeeuEoZp2TLUg8jbqeYR226Jd0",
    authDomain: "testhtml-218f5.firebaseapp.com",
    databaseURL: "https://testhtml-218f5-default-rtdb.firebaseio.com",
    projectId: "testhtml-218f5",
    storageBucket: "testhtml-218f5.appspot.com",
    messagingSenderId: "1062468553888",
    appId: "1:1062468553888:web:7cf9c878017d3156838dc5",
    measurementId: "G-49C0RFTPGJ"
  };
firebase.initializeApp(firebaseConfig);

// Reference messages collection
var messagesRef = firebase.database().ref('messages');

// Listen for form submit
document.getElementById('contactForm').addEventListener('submit', submitForm);

// Submit form
function submitForm(e){
  e.preventDefault();

  // Get values
  var name = getInputVal('name');
  var company = getInputVal('company');
  var email = getInputVal('email');
  var phone = getInputVal('phone');
  var message = getInputVal('message');

  // Save message
  saveMessage(name, company, email, phone, message);

  // Show alert
  document.querySelector('.alert').style.display = 'block';

  // Hide alert after 3 seconds
  setTimeout(function(){
    document.querySelector('.alert').style.display = 'none';
  },3000);

  // Clear form
  document.getElementById('contactForm').reset();
}

// Function to get get form values
function getInputVal(id){
  return document.getElementById(id).value;
}

// Save message to firebase
function saveMessage(name, company, email, phone, message){
  var newMessageRef = messagesRef.push();
set(ref(db, 'users/' + name), {
    username: name,
    email: email,
    phone : phone,
    message : message
  });
}