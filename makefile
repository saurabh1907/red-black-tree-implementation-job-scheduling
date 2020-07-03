JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        src/risingCity.java \
        src/City.java \
        src/Color.java \
  	src/ConstructionService.java \
        src/OperationsService.java \
  	src/QueueNode.java \
        src/RBTNode.java \
  	src/Building.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
