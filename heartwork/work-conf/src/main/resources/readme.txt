#######
versions:set -DnewVersion=0.5.5  -N versions:update-child-modules
clean test spring-boot:repackage