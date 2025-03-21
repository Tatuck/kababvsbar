with open("mapc.txt", "w+") as f:
    content = "\n".join([",".join(["0" for _ in range(60)])]*80)
    f.write(content)