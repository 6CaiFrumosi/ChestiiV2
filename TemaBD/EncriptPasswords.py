from cx_Oracle import makedsn, connect

hostName = "bd-dc.cs.tuiasi.ro"
portNumber = "1539"
serviceName = "orcl"
user = "bd083"
password = "Leloluch1998"

dsn_tns = makedsn(hostName, portNumber, service_name=serviceName)
conn = connect(user=user, password=password, dsn=dsn_tns)


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


if __name__ == "__main__":
    key = "ana are mere"
    cur = conn.cursor()
    cur.execute("select id, password from autentificare")
    selects = [[list(x)[0], list(x)[1]] for x in cur]

    encp_passwords = [[x[0], xorCipher(x[1], key)] for x in selects]
    for id, enc in encp_passwords:
        cur.execute(f"update autentificare set password='{enc}' where id = {id}")
    cur.execute("commit")
    cur.close()