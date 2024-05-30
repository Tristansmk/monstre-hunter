JAVAC = javac
JAVA = java
JAR = jar
SRC_DIR = ./src
BIN_DIR = ./bin
MAIN_CLASS = Main
JAR_NAME = 'Monstre Hunter.jar'
# Liste des fichiers source Java
JAVA_FILES := $(wildcard $(SRC_DIR)/Main/*.java $(SRC_DIR)/game/*.java $(SRC_DIR)/ui/*.java)

.PHONY: all
all: $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(JAVA_FILES)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

.PHONY: run
run:
	$(JAVA) -cp $(BIN_DIR) Main.$(MAIN_CLASS)

.PHONY: doc
doc:
	javadoc -d docs $(JAVA_FILES)

.PHONY: clean
clean:
	rm -rf $(BIN_DIR) $(JAR_NAME) docs