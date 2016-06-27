# vidsnip

Video Snippet-based Social Network.

[Prerequisites] - [Building] - [Running] - [License]

## Prerequisites

To build and run Vidsnip, you need:

 - Java 8
 - [MySQL]
 - [Neo4j]
 - [Maven]

On a bare Ubuntu box, use: (tested on 16.04)

```
# wget -O - https://debian.neo4j.org/neotechnology.gpg.key | apt-key add -
# echo 'deb http://debian.neo4j.org/repo stable/' > /etc/apt/sources.list.d/neo4j.list
# apt-get update
# apt-get install openjdk-8-jdk openjdk-8-jre mysql-server neo4j maven
```

## Building

```shell
$ git clone https://github.com/vidsnip/vidsnip.git
$ cd vidsnip
$ mvn install
```

By default, Vidsnip attempts to use these credentials:

 - **MySQL** username: `vidsnip`, password: `vidsnip`, database: `vidsnip`
 - **Neo4J** username: `neo4j`, password: `vidsnip` (Configure by visiting
   http://localhost:7474)

So set those up accordingly. (Or change the credentials in the `.properties`
files.)

## Running

```shell
$ mvn spring-boot:run
```

## License

[GPL-3]

[Prerequisites]: #prerequisites
[Building]: #building
[Running]: #running
[License]: #license

[MySQL]: https://www.mysql.com/
[Neo4j]: https://neo4j.com
[Maven]: https://maven.apache.org/
[GPL-3]: ./LICENSE
