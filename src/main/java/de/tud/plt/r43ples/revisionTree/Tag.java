package de.tud.plt.r43ples.revisionTree;

public class Tag extends ReferenceToCommit {

	public Tag(String uri, String name, Commit ref) {
		super(uri, name, ref);
	}

	public Tag(String uri) {
		super(uri);
	}
}
