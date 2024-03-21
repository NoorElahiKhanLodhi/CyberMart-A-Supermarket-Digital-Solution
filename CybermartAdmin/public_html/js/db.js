
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
var database = firebase.database();

// ADD PRODUCTS TO DATABASE AND GENERATE QR CODE
function add_product() {
    firebase.initializeApp(firebaseConfig);
var database = firebase.database();
    var pid = document.getElementById("pid").value;
    var pname = document.getElementById("pname").value;
    var quantity = document.getElementById("quantity").value;
    var price = document.getElementById("price").value;

    var ref = database.ref('cybermart/products/'+pid); //Save inside

    if(pid!="" & pname!="" & quantity!="" & price!=""){

var inputData = {
    pid: pid,
    pname: pname,
    quantity: quantity,
    price: price
};

//firebase start
ref.set(inputData)
   .then(function() {
       alert('Product '+pname+' registered successfully!\nCheck the QR code in Downloads.');
       var qr_text = pid+","+pname+","+quantity+","+price;
        generateQRCode(qr_text);
   })
   .catch(function(error) {
       console.error('Product '+pname+" could not be registered due to error: " + error);
   });
//firebase end

var allInputs = document.querySelectorAll('input');
allInputs.forEach(singleInput => singleInput.value = '');



}else{alert("Fill All Information!");}
}

//ADD TROLLEYS TO DATABASE AND GENERATE QR CODE
function add_trolley() {
    var tid = document.getElementById("tid").value;
    var status = "free";
    var bid = "";
    
    var ref = database.ref('cybermart/trolleys/'+tid); //Save inside

    if(tid!="" & status!=""){

var inputData = {
    tid: tid,
    status: status,
    bid: bid
};

//firebase start
ref.set(inputData)
   .then(function() {
       alert('Trolley '+tid+' registered successfully!\nCheck the QR code in Downloads.');
       generateQRCode(tid);
   })
   .catch(function(error) {
       console.error('Trolley '+tid+" could not be registered due to error: " + error);
   });
//firebase end

var allInputs = document.querySelectorAll('input');
allInputs.forEach(singleInput => singleInput.value = '');
    }else{alert("Fill All Information!");}
}


////// QR CODE GENERATOR
function generateQRCode(qr_text) {
    
    // Create a QR code
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        text: qr_text,
        width: 128,
        height: 128
    });

    // Create a download link for the QR code
    var qrCodeDataUrl = document.getElementById("qrcode").getElementsByTagName("canvas")[0].toDataURL();
    var downloadLink = document.createElement("a");
    downloadLink.href = qrCodeDataUrl;
    downloadLink.download = qr_text+".png";
    downloadLink.click();
}











// Send a POST request to your Java server (server.java)
var xhr = new XMLHttpRequest();
xhr.open("POST", "product_qr.java", true);
xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
        // Handle the response from the Java server, if needed
        var response = xhr.responseText;
        alert("Server Response: " + response);        
    }
};

xhr.send(JSON.stringify(inputData));
// JAVA server