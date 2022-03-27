from flask import Flask, redirect, url_for, render_template, request, session, flash, g
from datetime import timedelta
from cx_Oracle import makedsn, connect
from datetime import datetime

app = Flask(__name__)
app.secret_key= "Greetings!"
app.permanent_session_lifetime = timedelta(minutes=20)

hostName = "bd-dc.cs.tuiasi.ro"
portNumber = "1539"
serviceName = "orcl"
user = "bd083"
password = "(:)"

dsn_tns = makedsn(hostName, portNumber, service_name=serviceName)
conn = connect(user=user, password=password, dsn=dsn_tns)

key = "ana are mere"

def xorCipher(data, key):
    keyPrime = None
    if len(key) > len(data):
        keyPrime = key[:len(data)]
    else:
        keyPrime = key

    nr = len(data)
    mod = len(key)

    out = [chr(ord(data[i]) ^ ord(key[i % mod])) for i in range(len(data))]

    return ''.join(out)

@app.route("/")
def home():
    return render_template("index.html")

@app.route("/user", methods = ["POST", "GET"])
def usr():
    custommer = None
    ph_nr = None
    email = None
    if "user" in session:
        user = session["user"]
        id = session["id"]

        if request.method == "POST":

            custommer = request.form["custommer"]
            product = request.form["product"]
            cuantity = request.form["cuantity"]
            ph_nr = request.form["ph_nr"]
            email = request.form["email"]

            if custommer == "":
                flash("Custommer field not completed", "info")
                return redirect(url_for("usr"))

            if product == "":
                flash("Product field not completed", "info")
                return redirect(url_for("usr"))


            cur = conn.cursor()
            cur.execute(f"select code from productcodes where name='{product}'")
            prod_codes=[]
            for x in cur:
                prod_codes.append(x)
            cur.close()

            session.pop("custommer", None)
            session.pop("ph_nr", None)
            session.pop("email", None)

            session["custommer"] = custommer
            session["ph_nr"] = ph_nr
            session["email"] = email

            if not prod_codes:
                flash("Product unavailable", "info")
                return redirect(url_for("usr"))

            today= datetime.now().strftime("%d/%m/%Y %H:%M:%S")
            cur = conn.cursor()
            cur.execute(f"insert into orders_lvl_2 values({id}, '{custommer}',(select TO_DATE('{today}','dd/mm/yyyy HH24:MI:SS') from dual) , '{prod_codes[0][0]}', {cuantity}, '{ph_nr}', '{email}')")
            cur.execute("commit")
            cur.close()
            flash("Command was send!", "info")
        else:
            if "custommer" in session:
                custommer = session["custommer"]

            if "ph_nr" in session:
                ph_nr = session["ph_nr"]

            if "email" in session:
                email = session["email"]

        return render_template("user.html", user=user, custommer=custommer, ph_nr=ph_nr, email=email)
    else:
        flash("You are not logged in", "info")
        return redirect(url_for("login"))

@app.route("/login", methods=["POST", "GET"])
def login():
    if request.method == "POST":

        session.permanent = True
        user = request.form["username"]
        password=request.form["password"]

        cur = conn.cursor()
        cur.execute(f"select id, password from autentificare where username='{user}'")

        credentials = []
        for data in cur:
            credentials.append(data)

        cur.close()
        print(credentials)
        if len(credentials) !=1:
            flash("Incorrect Username or Password", "info")
            return  redirect(url_for("login"))

        password = xorCipher(password, key)
        if password not in credentials[0]:
            flash("Incorrect Username or Password", "info")
            return  redirect(url_for("login"))

        session["user"] = user
        session["id"] = credentials[0][0]

        return redirect(url_for("usr"))
    else:
        if "user" in session:
            flash("Already logged in", "info")
            return redirect(url_for("usr"))
        return render_template("login.html")

@app.route("/logout")
def logout():
    flash(f"You have been logged out ", "info")
    session.pop("user", None)
    session.pop("id", None)
    session.pop("custommer", None)
    session.pop("ph_nr", None)
    session.pop("email", None)
    return redirect(url_for("login"))

@app.route("/table/<type>")
def table(type):
    if "user" in session:
        cur=conn.cursor()
        heads=None
        if type == "orders":
            heads = ["MadeBy,", "Custommer,", "DateOfSubmmit,", "PartCode,", "Quantity,", "PhoneNr,", "Email"]
            cur.execute("select * from orders_lvl_2")
        elif type == "products":
            heads = ["Product", "Code"]
            cur.execute("select * from productcodes")

        else:
            cur.close()
            return redirect(url_for("home"))

        selection=[]
        for data in cur:
            selection.append(data)
        cur.close()
        #return redirect(url_for("showTable", heads=heads, selection=selection))
        return showTable(heads, selection)


    else:
        flash("You are not logged in", "info")
        return redirect(url_for("login"))



def showTable(heads, selection):
    #cur = conn.cursor()
    #cur.execute("select * from orders_lvl_2")
    names = []
    commands=[]
    for row in selection:
        names.append(row[0])
        commands.append(row[1:])

    #cur.close()
    #heads=["MadeBy", "Custommer", "DateOfSubmmit", "PartCode", "Quantity", "PhoneNr", "Email"]
    rows=list(zip(names, commands))

    return render_template("table.html", heads=heads, names=names, rows=rows)


@app.route("/delete", methods = ["POST", "GET"])
def deleteCommand():
    if "user" not in session:
        return redirect(url_for("login"))
    else:
        user=None
        if request.method == "POST":
            user = session["user"]
            custommer=request.form["custommer"]
            product=request.form["product"]
            cur=conn.cursor()
            product_codes=[]
            cur.execute(f"select code from productcodes where name='{product}'")
            for data in cur:
                product_codes.append(data)

            cur.execute(f"delete from orders_lvl_2 where onName='{custommer}' and partCode='{product_codes[0][0]}'")
            cur.execute("commit")
            cur.close()

        return render_template("delete.html", user=user)

@app.route("/update", methods = ["POST", "GET"])
def update():
    if "user" in session:
        id=session["id"]
        if request.method == "POST":
            type=request.form["type"]
            if type == "onName" or type == "partCode" or type == "phoneNr" or type == "email":
                type="'"+type+"'"
            elif type != "madeById" and type!="commandDate" and type !="quantity":
                flash("Update type not avaible", "info")
                return redirect(url_for("update"))

            changeTo = request.form["changeTo"]
            if changeTo == "":
                flash(url_for("Value of the new entry invalid"))
                return redirect(url_for("update"))

            cur = conn.cursor()
            cur.execute(f"update orders_lvl_2 set {type}={changeTo} where madeById={id}")
            cur.execute("commit")
            cur.close()
        return render_template("update.html")
    else:
        flash("You are not logged in")
        return redirect(url_for("usr"))

if __name__=="__main__":
    app.run(debug=True)



