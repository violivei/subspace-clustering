#ifndef DSWEIGHTSDIMS_H
#define	DSWEIGHTSDIMS_H

class DSWeightsDims {
public:
    int dimIndex;
    double weight;
    double dsWeight;
};

bool sortDSWeightsDims(const DSWeightsDims& dswd1, const DSWeightsDims& dswd2);
bool sortDSWeightsIndex(const DSWeightsDims& dswd1, const DSWeightsDims& dswd2);
bool operator==(const DSWeightsDims& dswd1, const DSWeightsDims& dswd2);


#endif	/* DSWEIGHTSDIMS_H */

