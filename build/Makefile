ifeq ($(OS),Windows_NT)
	RM=del
	RMDIR=rmdir /s /q
else
	RM=rm -f
	RMDIR=rm -rf
endif

CC=javac
SRCDIR=../src
EXAMPLEDIR=../examples

EXAMPLES=DataTypesExample

all: datatypes

datatypes:
	$(CC) -d . $(SRCDIR)/DataTypes/*.java

examples: datatypes $(EXAMPLES)

DataTypesExample: $(EXAMPLEDIR)/DataTypesExample.java
	$(CC) -d . $^

clean:
	$(RMDIR) datatypes
	$(RM) *.class