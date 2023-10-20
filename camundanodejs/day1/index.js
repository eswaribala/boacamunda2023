var express = require("express");
var fs = require('fs')
var https = require('https');
var bodyParser =require("body-parser");
var config=require("./config");
var cors=require("cors");
var app = express();
app.use(cors());
//app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json())
const ZB = require('zeebe-node')

app.get("/start", function(request, response) {
    const zbc = new ZB.ZBClient({
        // loglevel: 'DEBUG',
        camundaCloud: {
            clientId: "dRCjx_6~YCbnKSDOcmpw_DuNdf5sUdRY",
            clientSecret: "gXpG8CM7LSaV4obM2H3i72hP_1ut8Ay5Z6VfsY3aAZUgIdOt0drUBPagpxYveh-_",
            clusterId: "67517168-7d3d-4011-b2f4-89ac5e79bde9",
            clusterRegion: "dsm-1",
        },
    });
    zbc.createProcessInstance('Process_1wc4mx2', {}).then(console.log);
    response.send({"message":"Connected to Camunda"});
});

app.set('port',config.port);
app.listen(app.get('port'), function(){
    console.log('The server is running, ' +
        ' please open your browser at http://localhost:%s',
        app.get('port'));
});

/*
void (async () => {
    const zbc = new ZB.ZBClient({
        // loglevel: 'DEBUG',
         camundaCloud: {
           clientId: "dRCjx_6~YCbnKSDOcmpw_DuNdf5sUdRY",
           clientSecret: "gXpG8CM7LSaV4obM2H3i72hP_1ut8Ay5Z6VfsY3aAZUgIdOt0drUBPagpxYveh-_",
           clusterId: "67517168-7d3d-4011-b2f4-89ac5e79bde9",
           clusterRegion: "dsm-1",
         },
    });
    zbc.createProcessInstance('Process_1wc4mx2', {}).then(console.log);

})()
*/
