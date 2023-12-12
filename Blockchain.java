import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
class Block
{
private int index; private long timestamp;
private String previousHash; private String hash;
private String data; private int nonce;
public Block(int index, String previousHash, String data)
{
this.index = index;
this.timestamp = new Date().getTime(); this.previousHash = previousHash; this.data = data;
this.nonce = 0;
this.hash = calculateHash();
}
public String calculateHash()
{
try
{
MessageDigest digest = MessageDigest.getInstance("SHA-256"); String input = index +
timestamp + previousHash + data + nonce; byte[] hashBytes = digest.digest(input.getBytes());
StringBuilder hexString = new StringBuilder();
for (byte hashByte : hashBytes)
{
String hex = Integer.toHexString(0xff &hashByte); if (hex.length() == 1)
{
hexString.append('0');
}
hexString.append(hex);
}
return hexString.toString();
}
catch (NoSuchAlgorithmException e)
{
e.printStackTrace();
}
return null;
}
public void mineBlock(int difficulty)
{
String target = new String(new char[difficulty]).replace('\0', '0'); while (!hash.substring(0,
difficulty).equals(target))
{
nonce++;
hash = calculateHash();
}
System.out.println("Block mined: " + hash);
}
public int getIndex() { return index;
}
public long getTimestamp() { return timestamp;
}
public String getPreviousHash() { return previousHash;

}
public String getHash() { return hash;
}
public String getData()
{
return data;
}
public static void main(String args[]){ Block b=new
Block(1,"3a42c503953909637f78dd8c99b3b85ddde362415585afc11901bdefe8349102","hai");
b.calculateHash();
b.mineBlock(1); b.getIndex(); b.getTimestamp(); b.getPreviousHash(); b.getHash();
b.getData();
}
}
public class Blockchain
{
private List<Block> chain;
private int difficulty;
public Blockchain(int difficulty)
{
this.chain = new ArrayList<>();
this.difficulty = difficulty;
// Create the genesis 
    createGenesisBlock();
}
private void createGenesisBlock()
{
    Block genesisBlock = new Block(0, "0", "Genesis Block");
genesisBlock.mineBlock(difficulty);
chain.add(genesisBlock);
}
public Block getLatestBlock() { return chain.get(chain.size() - 1);
}
public void addBlock(Block newBlock)
{
newBlock.mineBlock(difficulty); chain.add(newBlock);
}
public boolean isChainValid()
{
for (int i = 1; i <chain.size(); i++)
{
Block currentBlock = chain.get(i);
Block previousBlock = chain.get(i - 1);
if (!currentBlock.getHash().equals(currentBlock.calculateHash()))
{
System.out.println("Invalid hash for Block " + currentBlock.getIndex());
return false;
}
if (!previousBlock.getHash().equals(currentBlock.getPreviousHash()))
{
System.out.println("Invalid previous hash for Block " + currentBlock.getIndex());
return false;
}}

return true;
}
public static void main(String[] args) { Blockchain blockchain = new Blockchain(4);
Block block1 = new Block(1, blockchain.getLatestBlock().getHash(), "Data 1");
blockchain.addBlock(block1);
Block block2 = new Block(2, blockchain.getLatestBlock().getHash(), "Data 2");
blockchain.addBlock(block2);
Block block3 = new Block(3, blockchain.getLatestBlock().getHash(), "Data 3");
blockchain.addBlock(block3);
System.out.println("Blockchain is valid: " + blockchain.isChainValid());
}
}