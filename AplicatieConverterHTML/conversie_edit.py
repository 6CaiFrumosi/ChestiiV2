
import os

from PyQt5 import QtCore, QtGui, QtWidgets

from converter import convert

class Ui_Form(object):
    def setupUi(self, Form):
        Form.setObjectName("Form")
        Form.resize(1102, 522)
        self.Title = QtWidgets.QLabel(Form)
        self.Title.setGeometry(QtCore.QRect(160, 10, 621, 46))
        font = QtGui.QFont()
        font.setPointSize(20)
        font.setBold(True)
        font.setWeight(75)
        self.Title.setFont(font)
        self.Title.setAlignment(QtCore.Qt.AlignCenter)
        self.Title.setObjectName("Title")
        self.path_edit_line = QtWidgets.QLineEdit(Form)
        self.path_edit_line.setGeometry(QtCore.QRect(90, 70, 761, 28))
        self.path_edit_line.setObjectName("path_edit_line")
        self.Path_Label = QtWidgets.QLabel(Form)
        self.Path_Label.setGeometry(QtCore.QRect(10, 60, 77, 38))
        font = QtGui.QFont()
        font.setPointSize(25)
        self.Path_Label.setFont(font)
        self.Path_Label.setObjectName("Path_Label")
        self.search_b = QtWidgets.QPushButton(Form)
        self.search_b.setGeometry(QtCore.QRect(860, 70, 121, 31))
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Fixed, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.search_b.sizePolicy().hasHeightForWidth())
        self.search_b.setSizePolicy(sizePolicy)
        self.search_b.setMinimumSize(QtCore.QSize(0, 0))
        self.search_b.setMaximumSize(QtCore.QSize(668, 123))
        self.search_b.setObjectName("search_b")
        self.textBrowser = QtWidgets.QTextBrowser(Form)
        self.textBrowser.setEnabled(True)
        self.textBrowser.setGeometry(QtCore.QRect(29, 123, 821, 361))
        self.textBrowser.setSizeIncrement(QtCore.QSize(0, 0))
        self.textBrowser.setObjectName("textBrowser")
        self.convert_b = QtWidgets.QPushButton(Form)
        self.convert_b.setGeometry(QtCore.QRect(870, 130, 201, 41))
        self.convert_b.setObjectName("convert_b")
        self.valid_b = QtWidgets.QPushButton(Form)
        self.valid_b.setGeometry(QtCore.QRect(870, 190, 201, 41))
        self.valid_b.setObjectName("valid_b")
        
        self.search_b.clicked.connect(self.click_search)
        self.valid_b.clicked.connect(self.click_valid_b)
        self.convert_b.clicked.connect(self.click_convert_b)
        self.retranslateUi(Form)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        _translate = QtCore.QCoreApplication.translate
        Form.setWindowTitle(_translate("Form", "Form"))
        self.Title.setText(_translate("Form", "Conversia unui text in fisier html"))
        self.Path_Label.setText(_translate("Form", " Path"))
        self.search_b.setText(_translate("Form", "Search"))
        self.convert_b.setText(_translate("Form", "Convert to HTML"))
        self.valid_b.setText(_translate("Form", "Send to validation"))
    
    def click_search(self):
        path=self.path_edit_line.text()
        if os.path.isfile(path):
            self.textBrowser.append("\nFisierul a fost gasit cu succes")
        else:
            self.textBrowser.append("\nFisierul nu exista")

    def click_valid_b(self):
        path=self.path_edit_line.text()
        os.system("python3 converter.py "+path)
        os.system("./regex")

    def click_convert_b(self):
        path=self.path_edit_line.text()        
        text=convert(path)
        self.textBrowser.append(text)

    
    

if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    Form = QtWidgets.QWidget()
    ui = Ui_Form()
    ui.setupUi(Form)
    Form.show()
    sys.exit(app.exec_())
