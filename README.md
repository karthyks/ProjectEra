# ProjectEra
Working prototype of a Microservices Architecture using gRpc

![alt text](https://github.com/karthyks/ProjectEra/blob/master/MicroservicesArchitecture.png?raw=true)

<br/><br/><br/>

<b>ServerHook:</b>
1. Passes systemInfo to the server, for eg. server load, memory usage, name, opened port info etc.
This information can be helpful to write a powerful consensus protocol, to distribute the computing among the connected nodes.

<b>Module:</b><br/>
Each module is hooked to the subserver either from the same machine or from other machines. By doing this, computing and storage
can be distributed among the nodes effectively.

<br><br>
Clients like mobile devices and websites contact the servers through <b>RestAPI</b>, based on the request, the subservers fetch informations from the respective modules and returns the response.
