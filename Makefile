all: MainWindow

run: MainWindow
	java gui.MainWindow

MainWindow:
	javac -sourcepath . ./**/*.java

jar: MainWindow
	jar cvfm SpaceInvaders.jar MANIFEST ./**/*.class resources/

.PHONY: clean

clean:
	rm -rf ./**/*.class *.jar
