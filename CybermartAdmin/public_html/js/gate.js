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
const database = firebase.database();

function domReady(fn) { 
    if ( 
        document.readyState === "complete" || 
        document.readyState === "interactive"
    ) { 
        setTimeout(fn, 1000); 
    } else { 
        document.addEventListener("DOMContentLoaded", fn); 
    } 
} 

domReady(function () { 
    function onScanSuccess(decodeText, decodeResult) { 
        // Use the scanned QR code as the trolley ID
        const trolleyId = decodeText;

        // Check the status of the trolley in the Firebase database
        const trolleyRef = database.ref('cybermart/trolleys/' + trolleyId);
        trolleyRef.once('value', function(snapshot) {
            const trolley = snapshot.val();
            if (trolley) {
                const status = trolley.status;

                if(status == "paid"){
                alert(`Trolley ID: ${trolleyId}\nStatus: ${status}\nYour trolley is allowed to pass!`);
                
                /// Arudino signal to java

                }else if(status == "billing"){
                alert(`Trolley ID: ${trolleyId}\nStatus: ${status}\nYour Bill is not paid so trolley is not allowed to pass!`);
            }else{
                alert(`Trolley ID: ${trolleyId}\nStatus: ${status}\nYour trolley is not billed not allowed!`);
            }               

            } else {
                alert(`Trolley ID: ${trolleyId}\nStatus: Trolley not found`);
            }
        });
    } 
    

    let htmlscanner = new Html5QrcodeScanner( 
        "my-qr-reader", 
        { fps: 10, qrbos: 250 } 
    );
    htmlscanner.render(onScanSuccess); 
});
