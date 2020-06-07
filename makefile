JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        risingCity.java \
        City.java \
        Color.java \
  	ConstructionService.java \
        OperationsService.java \
  	QueueNode.java \
        RBTNode.java \
  	Building.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class