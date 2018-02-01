# IMCE Ontologies

2017-01-31:

- baseline: feature/IMCEIS-1715-create-temporary-branch-of-ontologie

- kept a single OML representation (*.oml); other representations (e.g., *.owl) can be produced by conversion.

- The contents of `*.oml` files is identical to corresponding `*.oml` files in feature/IMCEIS-1715-create-temporary-branch-of-ontologie;
  the difference between them is the location of these files.
  
  In feature/IMCEIS-1715-create-temporary-branch-of-ontologie, the `*.oml` files follow the 1-level organization convention;
  that is, content authored by the IMCE project at JPL is prefixed by the IMCE organization qualified name: `http://imce.jpl.nasa.gov/`
  and mapped by the catalog in a similar way: `file:./imce.jpl.nasa.gov/`.
  
  In this branch, the `*.oml` files follow the proposed 2-level organizaiton convention for the file location only;
  the IRI retains the same 1-level organization convention where the categorization is done at the tail of the IRI rather than at a prefix of the IRI.
  This requires OASIS XML Catalog rewrite rules mapping suffix-categorized IRIs to prefix-categorized file locations.
  
  For example:
  
  ```
	<rewriteURI
			rewritePrefix="file:./imce.jpl.nasa.gov/embeddings/magicdraw.sysml/foundation/mission/mission-embedding"
			uriStartString="http://imce.jpl.nasa.gov/foundation/mission/mission-embedding"/>
	<rewriteURI
			rewritePrefix="file:./imce.jpl.nasa.gov/vocabularies/foundation/mission/mission"
			uriStartString="http://imce.jpl.nasa.gov/foundation/mission/mission"/>
  ```
  
  This works because the semantics of the OASIS XML Catalog is that when multiple rewrite rules apply to a given URI,
  preference is given to the rule with the longest uri start string.
  
  As far as local files are concerned, this facilitates partitioning OML content by category with the possibility
  of exploring alternate embeddings even for the same modeling tool as summarized below:
  
  | Organization + Category file prefix | Description |
  | --- | --- |
  | [imce.jpl.nasa.gov/vocabularies](vocabularies/imce.jpl.nasa.gov/vocabularies) | Specification of the IMCE vocabularies independently of any embedding. |
  | [imce.jpl.nasa.gov/embeddings/magicdraw.sysml](vocabularies/imce.jpl.nasa.gov/embeddings/magicdraw.sysml) | The original embedding of the IMCE vocabularies as an extension of MagicDraw's SysML. |
  | `imce.jpl.nasa.gov/embeddings/magicdraw.ccm` (example only!) | An embedding of the IMCE vocabularies as an extension of the Magicdraw-based Cameo Concept Modeler. (example only!) |
  
  
  